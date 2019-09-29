package cz.levinzonr.spotistats.injection.modules

import cz.levinzonr.spotistats.inititializers.AppInitializer
import cz.levinzonr.spotistats.inititializers.AppInitializerImpl
import dk.nodes.arch.domain.executor.Executor
import dk.nodes.arch.domain.executor.ThreadExecutor
import org.koin.dsl.module

val executorModule = module {
    single<Executor> { ThreadExecutor() }
    single<AppInitializer> { AppInitializerImpl() }
}