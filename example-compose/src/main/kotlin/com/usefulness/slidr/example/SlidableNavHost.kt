package com.usefulness.slidr.example

import androidx.activity.compose.LocalOnBackPressedDispatcherOwner
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.tween
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.rememberSwipeableState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveableStateHolder
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.lifecycle.viewmodel.compose.LocalViewModelStoreOwner
import androidx.navigation.NavGraph
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.DialogHost
import androidx.navigation.compose.DialogNavigator
import androidx.navigation.compose.LocalOwnersProvider
import androidx.navigation.createGraph
import androidx.navigation.get
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

/**
 * Provides in place in the Compose hierarchy for self contained navigation to occur.
 *
 * Once this is called, any Composable within the given [NavGraphBuilder] can be navigated to from
 * the provided [navController].
 *
 * The builder passed into this method is [remember]ed. This means that for this NavHost, the
 * contents of the builder cannot be changed.
 *
 * @param navController the navController for this host
 * @param startDestination the route for the start destination
 * @param modifier The modifier to be applied to the layout.
 * @param route the route for the graph
 * @param builder the builder used to construct the graph
 */
@Composable
@ExperimentalAnimationApi
fun SlidableNavHost(
    navController: NavHostController,
    startDestination: String,
    modifier: Modifier = Modifier,
    route: String? = null,
    builder: NavGraphBuilder.() -> Unit,
) {
    SlidableNavHost(
        navController = navController,
        graph = remember(route, startDestination, builder) {
            navController.createGraph(startDestination, route, builder)
        },
        modifier = modifier,
    )
}

/**
 * Provides in place in the Compose hierarchy for self contained navigation to occur.
 *
 * Once this is called, any Composable within the given [NavGraphBuilder] can be navigated to from
 * the provided [navController].
 *
 * @param navController the navController for this host
 * @param graph the graph for this host
 * @param modifier The modifier to be applied to the layout.
 */
@OptIn(ExperimentalMaterialApi::class)
@ExperimentalAnimationApi
@Composable
fun SlidableNavHost(
    navController: NavHostController,
    graph: NavGraph,
    modifier: Modifier = Modifier,
) {
    val lifecycleOwner = LocalLifecycleOwner.current
    val viewModelStoreOwner = checkNotNull(LocalViewModelStoreOwner.current) {
        "NavHost requires a ViewModelStoreOwner to be provided via LocalViewModelStoreOwner"
    }
    val onBackPressedDispatcherOwner = LocalOnBackPressedDispatcherOwner.current
    val onBackPressedDispatcher = onBackPressedDispatcherOwner?.onBackPressedDispatcher

    // on successful recompose we setup the navController with proper inputs
    // after the first time, this will only happen again if one of the inputs changes
    navController.setLifecycleOwner(lifecycleOwner)
    navController.setViewModelStore(viewModelStoreOwner.viewModelStore)
    if (onBackPressedDispatcher != null) {
        navController.setOnBackPressedDispatcher(onBackPressedDispatcher)
    }

    navController.graph = graph

    val saveableStateHolder = rememberSaveableStateHolder()

    // Find the ComposeNavigator, returning early if it isn't found
    // (such as is the case when using TestNavHostController)
    val composeNavigator = navController.navigatorProvider.get(SlidableComposeNavigator.NAME) as? SlidableComposeNavigator
        ?: return
    val visibleEntries by remember(navController.visibleEntries) {
        navController.visibleEntries.map { entries ->
            entries.filter { entry -> entry.destination.navigatorName == SlidableComposeNavigator.NAME }
        }
    }
        .collectAsState(emptyList())

    val backStackEntry = visibleEntries.lastOrNull()
    val lastThingOnBackstack = navController.previousBackStackEntry

    val scope = rememberCoroutineScope()
    lateinit var swipeAwayCallback: (SwipeToGoBackState) -> Unit
    val swipeableState = rememberSwipeableState(
        initialValue = SwipeToGoBackState.Default,
        confirmStateChange = { state ->
            when (state) {
                SwipeToGoBackState.Default -> true
                SwipeToGoBackState.SwipedEnd,
                SwipeToGoBackState.SwipedStart,
                -> {
                    swipeAwayCallback(state)
                    true
                }
            }
        },
    )
    swipeAwayCallback = { updatedStsate ->
        scope.launch {
            swipeableState.animateTo(targetValue = updatedStsate, tween())
            navController.popBackStack()
            swipeableState.snapTo(SwipeToGoBackState.Default)
        }
    }

    SlidableContainer(
        modifier = modifier,
        enabled = lastThingOnBackstack != null,
        background = {
            lastThingOnBackstack?.LocalOwnersProvider(saveableStateHolder) {
                (lastThingOnBackstack.destination as SlidableComposeNavigator.Destination).content(lastThingOnBackstack)
            }
        },
        foreground = {
            backStackEntry?.LocalOwnersProvider(saveableStateHolder) {
                (backStackEntry.destination as SlidableComposeNavigator.Destination).content(backStackEntry)
            }
        },
        swipeableState = swipeableState,
    )

    DisposableEffect(backStackEntry) {
        onDispose {
            visibleEntries.forEach(composeNavigator::markTransitionComplete)
        }
    }


    val dialogNavigator = navController.navigatorProvider.get("dialog") as? DialogNavigator ?: return

    // Show any dialog destinations
    DialogHost(dialogNavigator)
}

