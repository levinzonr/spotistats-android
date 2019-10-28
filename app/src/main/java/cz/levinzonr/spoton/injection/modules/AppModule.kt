package cz.levinzonr.spoton.injection.modules

import cz.levinzonr.spoton.presentation.injection.viewModels

val appModule = listOf(
        executorModule,
        interactorModule,
        restModule,
        repositoryModule,
        storageModule,
        viewModels,
        managerModule
)