(function() {
    'use strict';

    angular
        .module('pinfProApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('naseljeno-mesto', {
            parent: 'entity',
            url: '/naseljeno-mesto',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'NaseljenoMestos'
            },
            params: {'drzava':null},
            views: {
                'content@': {
                    templateUrl: 'app/entities/naseljeno-mesto/naseljeno-mestos.html',
                    controller: 'NaseljenoMestoController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
            }
        })
        .state('naseljeno-mesto-detail', {
            parent: 'entity',
            url: '/naseljeno-mesto/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'NaseljenoMesto'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/naseljeno-mesto/naseljeno-mesto-detail.html',
                    controller: 'NaseljenoMestoDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                entity: ['$stateParams', 'NaseljenoMesto', function($stateParams, NaseljenoMesto) {
                    return NaseljenoMesto.get({id : $stateParams.id});
                }]
            }
        })
        .state('naseljeno-mesto.new', {
            parent: 'naseljeno-mesto',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/naseljeno-mesto/naseljeno-mesto-dialog.html',
                    controller: 'NaseljenoMestoDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                nm_naziv: null,
                                nm_pttoznaka: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('naseljeno-mesto', null, { reload: true });
                }, function() {
                    //$state.go('naseljeno-mesto');
                });
            }]
        })
        .state('naseljeno-mesto.edit', {
            parent: 'naseljeno-mesto',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/naseljeno-mesto/naseljeno-mesto-dialog.html',
                    controller: 'NaseljenoMestoDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['NaseljenoMesto', function(NaseljenoMesto) {
                            return NaseljenoMesto.get({id : $stateParams.id});
                        }]
                    }
                }).result.then(function() {
                    $state.go('naseljeno-mesto', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('naseljeno-mesto.delete', {
            parent: 'naseljeno-mesto',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/naseljeno-mesto/naseljeno-mesto-delete-dialog.html',
                    controller: 'NaseljenoMestoDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['NaseljenoMesto', function(NaseljenoMesto) {
                            return NaseljenoMesto.get({id : $stateParams.id});
                        }]
                    }
                }).result.then(function() {
                    $state.go('naseljeno-mesto', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
