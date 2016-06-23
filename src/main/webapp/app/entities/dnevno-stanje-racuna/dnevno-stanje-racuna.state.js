(function() {
    'use strict';

    angular
        .module('pinfProApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('dnevno-stanje-racuna', {
            parent: 'entity',
            url: '/dnevno-stanje-racuna',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'DnevnoStanjeRacunas'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/dnevno-stanje-racuna/dnevno-stanje-racunas.html',
                    controller: 'DnevnoStanjeRacunaController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
            }
        })
        .state('dnevno-stanje-racuna-detail', {
            parent: 'entity',
            url: '/dnevno-stanje-racuna/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'DnevnoStanjeRacuna'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/dnevno-stanje-racuna/dnevno-stanje-racuna-detail.html',
                    controller: 'DnevnoStanjeRacunaDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                entity: ['$stateParams', 'DnevnoStanjeRacuna', function($stateParams, DnevnoStanjeRacuna) {
                    return DnevnoStanjeRacuna.get({id : $stateParams.id});
                }]
            }
        })
        .state('dnevno-stanje-racuna.new', {
            parent: 'dnevno-stanje-racuna',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/dnevno-stanje-racuna/dnevno-stanje-racuna-dialog.html',
                    controller: 'DnevnoStanjeRacunaDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                brojIzvoda: null,
                                datum: null,
                                prethodnoStanje: null,
                                prometUKorist: null,
                                prometNaTeret: null,
                                novoStanje: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('dnevno-stanje-racuna', null, { reload: true });
                }, function() {
                    $state.go('dnevno-stanje-racuna');
                });
            }]
        })
        .state('dnevno-stanje-racuna.edit', {
            parent: 'dnevno-stanje-racuna',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/dnevno-stanje-racuna/dnevno-stanje-racuna-dialog.html',
                    controller: 'DnevnoStanjeRacunaDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['DnevnoStanjeRacuna', function(DnevnoStanjeRacuna) {
                            return DnevnoStanjeRacuna.get({id : $stateParams.id});
                        }]
                    }
                }).result.then(function() {
                    $state.go('dnevno-stanje-racuna', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('dnevno-stanje-racuna.delete', {
            parent: 'dnevno-stanje-racuna',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/dnevno-stanje-racuna/dnevno-stanje-racuna-delete-dialog.html',
                    controller: 'DnevnoStanjeRacunaDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['DnevnoStanjeRacuna', function(DnevnoStanjeRacuna) {
                            return DnevnoStanjeRacuna.get({id : $stateParams.id});
                        }]
                    }
                }).result.then(function() {
                    $state.go('dnevno-stanje-racuna', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
