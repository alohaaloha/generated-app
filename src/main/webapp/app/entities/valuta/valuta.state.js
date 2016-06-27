(function() {
    'use strict';

    angular
        .module('pinfProApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('valuta', {
            parent: 'entity',
            url: '/valuta',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'Valutas'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/valuta/valutas.html',
                    controller: 'ValutaController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
            }
        })
        .state('valuta-detail', {
            parent: 'entity',
            url: '/valuta/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'Valuta'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/valuta/valuta-detail.html',
                    controller: 'ValutaDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                entity: ['$stateParams', 'Valuta', function($stateParams, Valuta) {
                    return Valuta.get({id : $stateParams.id});
                }]
            }
        })
        .state('valuta.new', {
            parent: 'valuta',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/valuta/valuta-dialog.html',
                    controller: 'ValutaDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                zvanicnaSifra: null,
                                nazivValute: null,
                                domicilna: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('valuta', null, { reload: true });
                }, function() {
                    //$state.go('valuta');
                });
            }]
        })
        .state('valuta.edit', {
            parent: 'valuta',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/valuta/valuta-dialog.html',
                    controller: 'ValutaDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Valuta', function(Valuta) {
                            return Valuta.get({id : $stateParams.id});
                        }]
                    }
                }).result.then(function() {
                    $state.go('valuta', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('valuta.delete', {
            parent: 'valuta',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/valuta/valuta-delete-dialog.html',
                    controller: 'ValutaDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Valuta', function(Valuta) {
                            return Valuta.get({id : $stateParams.id});
                        }]
                    }
                }).result.then(function() {
                    $state.go('valuta', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
