(function() {
    'use strict';

    angular
        .module('pinfProApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('vrsta-placanja', {
            parent: 'entity',
            url: '/vrsta-placanja',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'VrstaPlacanjas'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/vrsta-placanja/vrsta-placanjas.html',
                    controller: 'VrstaPlacanjaController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
            }
        })
        .state('vrsta-placanja-detail', {
            parent: 'entity',
            url: '/vrsta-placanja/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'VrstaPlacanja'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/vrsta-placanja/vrsta-placanja-detail.html',
                    controller: 'VrstaPlacanjaDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                entity: ['$stateParams', 'VrstaPlacanja', function($stateParams, VrstaPlacanja) {
                    return VrstaPlacanja.get({id : $stateParams.id});
                }]
            }
        })
        .state('vrsta-placanja.new', {
            parent: 'vrsta-placanja',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/vrsta-placanja/vrsta-placanja-dialog.html',
                    controller: 'VrstaPlacanjaDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                oznakaVrstePlacanja: null,
                                nazivVrstePlacanja: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('vrsta-placanja', null, { reload: true });
                }, function() {
                    $state.go('vrsta-placanja');
                });
            }]
        })
        .state('vrsta-placanja.edit', {
            parent: 'vrsta-placanja',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/vrsta-placanja/vrsta-placanja-dialog.html',
                    controller: 'VrstaPlacanjaDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['VrstaPlacanja', function(VrstaPlacanja) {
                            return VrstaPlacanja.get({id : $stateParams.id});
                        }]
                    }
                }).result.then(function() {
                    $state.go('vrsta-placanja', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('vrsta-placanja.delete', {
            parent: 'vrsta-placanja',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/vrsta-placanja/vrsta-placanja-delete-dialog.html',
                    controller: 'VrstaPlacanjaDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['VrstaPlacanja', function(VrstaPlacanja) {
                            return VrstaPlacanja.get({id : $stateParams.id});
                        }]
                    }
                }).result.then(function() {
                    $state.go('vrsta-placanja', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
