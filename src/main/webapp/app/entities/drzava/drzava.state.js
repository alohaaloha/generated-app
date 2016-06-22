(function() {
    'use strict';

    angular
        .module('pinfProApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('drzava', {
            parent: 'entity',
            url: '/drzava',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'Drzavas'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/drzava/drzavas.html',
                    controller: 'DrzavaController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
            }
        })
        .state('drzava-detail', {
            parent: 'entity',
            url: '/drzava/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'Drzava'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/drzava/drzava-detail.html',
                    controller: 'DrzavaDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                entity: ['$stateParams', 'Drzava', function($stateParams, Drzava) {
                    return Drzava.get({id : $stateParams.id});
                }]
            }
        })
        .state('drzava.new', {
            parent: 'drzava',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/drzava/drzava-dialog.html',
                    controller: 'DrzavaDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                dr_naziv: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('drzava', null, { reload: true });
                }, function() {
                    $state.go('drzava');
                });
            }]
        })
        .state('drzava.edit', {
            parent: 'drzava',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/drzava/drzava-dialog.html',
                    controller: 'DrzavaDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Drzava', function(Drzava) {
                            return Drzava.get({id : $stateParams.id});
                        }]
                    }
                }).result.then(function() {
                    $state.go('drzava', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('drzava.delete', {
            parent: 'drzava',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/drzava/drzava-delete-dialog.html',
                    controller: 'DrzavaDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Drzava', function(Drzava) {
                            return Drzava.get({id : $stateParams.id});
                        }]
                    }
                }).result.then(function() {
                    $state.go('drzava', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
