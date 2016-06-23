(function() {
    'use strict';

    angular
        .module('pinfProApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('pravno-lice', {
            parent: 'entity',
            url: '/pravno-lice',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'PravnoLice'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/pravno-lice/pravno-lice.html',
                    controller: 'PravnoLiceController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
            }
        })
        .state('pravno-lice-detail', {
            parent: 'entity',
            url: '/pravno-lice/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'PravnoLice'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/pravno-lice/pravno-lice-detail.html',
                    controller: 'PravnoLiceDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                entity: ['$stateParams', 'PravnoLice', function($stateParams, PravnoLice) {
                    return PravnoLice.get({id : $stateParams.id});
                }]
            }
        })
        .state('pravno-lice.new', {
            parent: 'pravno-lice',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/pravno-lice/pravno-lice-dialog.html',
                    controller: 'PravnoLiceDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                imeOdgovornogLica: null,
                                prezimeOdgovornogLica: null,
                                jmbg: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('pravno-lice', null, { reload: true });
                }, function() {
                    $state.go('pravno-lice');
                });
            }]
        })
        .state('pravno-lice.edit', {
            parent: 'pravno-lice',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/pravno-lice/pravno-lice-dialog.html',
                    controller: 'PravnoLiceDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['PravnoLice', function(PravnoLice) {
                            return PravnoLice.get({id : $stateParams.id});
                        }]
                    }
                }).result.then(function() {
                    $state.go('pravno-lice', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('pravno-lice.delete', {
            parent: 'pravno-lice',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/pravno-lice/pravno-lice-delete-dialog.html',
                    controller: 'PravnoLiceDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['PravnoLice', function(PravnoLice) {
                            return PravnoLice.get({id : $stateParams.id});
                        }]
                    }
                }).result.then(function() {
                    $state.go('pravno-lice', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
