package cz.levinzonr.spotistats.injection.modules

import cz.levinzonr.spotistats.presentation.injection.viewModels

val appModule = listOf(
        executorModule,
        interactorModule,
        restModule,
        repositoryModule,
        storageModule,
        viewModels
)