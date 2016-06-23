(function() {
    'use strict';

    angular
        .module('pinfProApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('analitika-izvoda', {
            parent: 'entity',
            url: '/analitika-izvoda',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'AnalitikaIzvodas'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/analitika-izvoda/analitika-izvodas.html',
                    controller: 'AnalitikaIzvodaController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
            }
        })
        .state('analitika-izvoda-detail', {
            parent: 'entity',
            url: '/analitika-izvoda/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'AnalitikaIzvoda'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/analitika-izvoda/analitika-izvoda-detail.html',
                    controller: 'AnalitikaIzvodaDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                entity: ['$stateParams', 'AnalitikaIzvoda', function($stateParams, AnalitikaIzvoda) {
                    return AnalitikaIzvoda.get({id : $stateParams.id});
                }]
            }
        })
        .state('analitika-izvoda.new', {
            parent: 'analitika-izvoda',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/analitika-izvoda/analitika-izvoda-dialog.html',
                    controller: 'AnalitikaIzvodaDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                duznik: null,
                                svrha: null,
                                poverilac: null,
                                datumPrijema: null,
                                datumValute: null,
                                racunDuznika: null,
                                modelZaduzenja: null,
                                pozivNaBrojZaduzenja: null,
                                racunPoverioca: null,
                                modelOdobrenja: null,
                                pozivNaBrojOdobrenja: null,
                                isHitno: false,
                                iznos: null,
                                tipGreske: null,
                                status: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('analitika-izvoda', null, { reload: true });
                }, function() {
                    $state.go('analitika-izvoda');
                });
            }]
        })
        .state('analitika-izvoda.edit', {
            parent: 'analitika-izvoda',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/analitika-izvoda/analitika-izvoda-dialog.html',
                    controller: 'AnalitikaIzvodaDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['AnalitikaIzvoda', function(AnalitikaIzvoda) {
                            return AnalitikaIzvoda.get({id : $stateParams.id});
                        }]
                    }
                }).result.then(function() {
                    $state.go('analitika-izvoda', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('analitika-izvoda.delete', {
            parent: 'analitika-izvoda',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/analitika-izvoda/analitika-izvoda-delete-dialog.html',
                    controller: 'AnalitikaIzvodaDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['AnalitikaIzvoda', function(AnalitikaIzvoda) {
                            return AnalitikaIzvoda.get({id : $stateParams.id});
                        }]
                    }
                }).result.then(function() {
                    $state.go('analitika-izvoda', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
