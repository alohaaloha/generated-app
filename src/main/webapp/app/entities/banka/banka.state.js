(function() {
    'use strict';

    angular
        .module('pinfProApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('banka', {
            parent: 'entity',
            url: '/banka',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'Bankas'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/banka/bankas.html',
                    controller: 'BankaController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
            }
        })
        .state('banka-detail', {
            parent: 'entity',
            url: '/banka/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'Banka'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/banka/banka-detail.html',
                    controller: 'BankaDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                entity: ['$stateParams', 'Banka', function($stateParams, Banka) {
                    return Banka.get({id : $stateParams.id});
                }]
            }
        })
        .state('banka.new', {
            parent: 'banka',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/banka/banka-dialog.html',
                    controller: 'BankaDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                sifraBanke: null,
                                pib: null,
                                naziv: null,
                                adresa: null,
                                email: null,
                                web: null,
                                telefon: null,
                                fax: null,
                                bankaInt: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('banka', null, { reload: true });
                }, function() {
                    $state.go('banka');
                });
            }]
        })
        .state('banka.edit', {
            parent: 'banka',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/banka/banka-dialog.html',
                    controller: 'BankaDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Banka', function(Banka) {
                            return Banka.get({id : $stateParams.id});
                        }]
                    }
                }).result.then(function() {
                    $state.go('banka', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('banka.delete', {
            parent: 'banka',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/banka/banka-delete-dialog.html',
                    controller: 'BankaDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Banka', function(Banka) {
                            return Banka.get({id : $stateParams.id});
                        }]
                    }
                }).result.then(function() {
                    $state.go('banka', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
