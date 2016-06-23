(function() {
    'use strict';

    angular
        .module('pinfProApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('r-tgs', {
            parent: 'entity',
            url: '/r-tgs',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'RTGS'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/r-tgs/r-tgs.html',
                    controller: 'RTGSController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
            }
        })
        .state('r-tgs-detail', {
            parent: 'entity',
            url: '/r-tgs/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'RTGS'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/r-tgs/r-tgs-detail.html',
                    controller: 'RTGSDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                entity: ['$stateParams', 'RTGS', function($stateParams, RTGS) {
                    return RTGS.get({id : $stateParams.id});
                }]
            }
        })
        .state('r-tgs.new', {
            parent: 'r-tgs',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/r-tgs/r-tgs-dialog.html',
                    controller: 'RTGSDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                idPoruke: null,
                                swiftKodBankeDuznika: null,
                                obracunskiRacunBankeDuznika: null,
                                swiftKodBankePoverioca: null,
                                obracunskiRacunBankePoverioca: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('r-tgs', null, { reload: true });
                }, function() {
                    $state.go('r-tgs');
                });
            }]
        })
        .state('r-tgs.edit', {
            parent: 'r-tgs',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/r-tgs/r-tgs-dialog.html',
                    controller: 'RTGSDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['RTGS', function(RTGS) {
                            return RTGS.get({id : $stateParams.id});
                        }]
                    }
                }).result.then(function() {
                    $state.go('r-tgs', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('r-tgs.delete', {
            parent: 'r-tgs',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/r-tgs/r-tgs-delete-dialog.html',
                    controller: 'RTGSDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['RTGS', function(RTGS) {
                            return RTGS.get({id : $stateParams.id});
                        }]
                    }
                }).result.then(function() {
                    $state.go('r-tgs', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
