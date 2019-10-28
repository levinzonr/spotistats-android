package cz.levinzonr.spoton.injection.modules

import cz.levinzonr.spoton.inititializers.AppInitializer
import cz.levinzonr.spoton.inititializers.AppInitializerImpl
import dk.nodes.arch.domain.executor.Executor
import dk.nodes.arch.domain.executor.ThreadExecutor
import org.koin.dsl.module

val executorModule = module {
    single<Executor> { ThreadExecutor() }
    single<AppInitializer> { AppInitializerImpl() }
}