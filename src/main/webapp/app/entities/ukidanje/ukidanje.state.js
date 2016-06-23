(function() {
    'use strict';

    angular
        .module('pinfProApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('ukidanje', {
            parent: 'entity',
            url: '/ukidanje',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'Ukidanjes'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/ukidanje/ukidanjes.html',
                    controller: 'UkidanjeController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
            }
        })
        .state('ukidanje-detail', {
            parent: 'entity',
            url: '/ukidanje/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'Ukidanje'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/ukidanje/ukidanje-detail.html',
                    controller: 'UkidanjeDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                entity: ['$stateParams', 'Ukidanje', function($stateParams, Ukidanje) {
                    return Ukidanje.get({id : $stateParams.id});
                }]
            }
        })
        .state('ukidanje.new', {
            parent: 'ukidanje',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/ukidanje/ukidanje-dialog.html',
                    controller: 'UkidanjeDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                datumUkidanja: null,
                                prenosNaRacun: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('ukidanje', null, { reload: true });
                }, function() {
                    $state.go('ukidanje');
                });
            }]
        })
        .state('ukidanje.edit', {
            parent: 'ukidanje',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/ukidanje/ukidanje-dialog.html',
                    controller: 'UkidanjeDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Ukidanje', function(Ukidanje) {
                            return Ukidanje.get({id : $stateParams.id});
                        }]
                    }
                }).result.then(function() {
                    $state.go('ukidanje', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('ukidanje.delete', {
            parent: 'ukidanje',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/ukidanje/ukidanje-delete-dialog.html',
                    controller: 'UkidanjeDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Ukidanje', function(Ukidanje) {
                            return Ukidanje.get({id : $stateParams.id});
                        }]
                    }
                }).result.then(function() {
                    $state.go('ukidanje', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
