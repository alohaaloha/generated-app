(function() {
    'use strict';

    angular
        .module('pinfProApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('kliring', {
            parent: 'entity',
            url: '/kliring',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'Klirings'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/kliring/klirings.html',
                    controller: 'KliringController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
            }
        })
        .state('kliring-detail', {
            parent: 'entity',
            url: '/kliring/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'Kliring'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/kliring/kliring-detail.html',
                    controller: 'KliringDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                entity: ['$stateParams', 'Kliring', function($stateParams, Kliring) {
                    return Kliring.get({id : $stateParams.id}).$promise;
                }]
            }
        })
        .state('kliring.new', {
            parent: 'kliring',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/kliring/kliring-dialog.html',
                    controller: 'KliringDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                idPoruke: null,
                                swwift_duznika: null,
                                obracunskiRacunDuznika: null,
                                swift_poverioca: null,
                                obracunskiRacunPoverioca: null,
                                ukupanIznos: null,
                                datumValute: null,
                                datum: null,
                                poslat: false,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('kliring', null, { reload: true });
                }, function() {
                    $state.go('kliring');
                });
            }]
        })
        .state('kliring.edit', {
            parent: 'kliring',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/kliring/kliring-dialog.html',
                    controller: 'KliringDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Kliring', function(Kliring) {
                            return Kliring.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('kliring', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('kliring.delete', {
            parent: 'kliring',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/kliring/kliring-delete-dialog.html',
                    controller: 'KliringDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Kliring', function(Kliring) {
                            return Kliring.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('kliring', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
