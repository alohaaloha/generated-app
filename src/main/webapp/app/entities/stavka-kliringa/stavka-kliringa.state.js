(function() {
    'use strict';

    angular
        .module('pinfProApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('stavka-kliringa', {
            parent: 'entity',
            url: '/stavka-kliringa',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'StavkaKliringas'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/stavka-kliringa/stavka-kliringas.html',
                    controller: 'StavkaKliringaController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
            }
        })
        .state('stavka-kliringa-detail', {
            parent: 'entity',
            url: '/stavka-kliringa/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'StavkaKliringa'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/stavka-kliringa/stavka-kliringa-detail.html',
                    controller: 'StavkaKliringaDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                entity: ['$stateParams', 'StavkaKliringa', function($stateParams, StavkaKliringa) {
                    return StavkaKliringa.get({id : $stateParams.id});
                }]
            }
        })
        .state('stavka-kliringa.new', {
            parent: 'stavka-kliringa',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/stavka-kliringa/stavka-kliringa-dialog.html',
                    controller: 'StavkaKliringaDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('stavka-kliringa', null, { reload: true });
                }, function() {
                    $state.go('stavka-kliringa');
                });
            }]
        })
        .state('stavka-kliringa.edit', {
            parent: 'stavka-kliringa',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/stavka-kliringa/stavka-kliringa-dialog.html',
                    controller: 'StavkaKliringaDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['StavkaKliringa', function(StavkaKliringa) {
                            return StavkaKliringa.get({id : $stateParams.id});
                        }]
                    }
                }).result.then(function() {
                    $state.go('stavka-kliringa', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('stavka-kliringa.delete', {
            parent: 'stavka-kliringa',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/stavka-kliringa/stavka-kliringa-delete-dialog.html',
                    controller: 'StavkaKliringaDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['StavkaKliringa', function(StavkaKliringa) {
                            return StavkaKliringa.get({id : $stateParams.id});
                        }]
                    }
                }).result.then(function() {
                    $state.go('stavka-kliringa', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
