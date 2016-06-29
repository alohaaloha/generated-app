(function() {
    'use strict';

    angular
        .module('pinfProApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('racun-pravnog-lica', {
            parent: 'entity',
            url: '/racun-pravnog-lica',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'RacunPravnogLicas'
            },
            params: { 'klijent' : null},
            views: {
                'content@': {
                    templateUrl: 'app/entities/racun-pravnog-lica/racun-pravnog-licas.html',
                    controller: 'RacunPravnogLicaController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
            }
        })
        .state('racun-pravnog-lica-detail', {
            parent: 'entity',
            url: '/racun-pravnog-lica/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'RacunPravnogLica'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/racun-pravnog-lica/racun-pravnog-lica-detail.html',
                    controller: 'RacunPravnogLicaDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                entity: ['$stateParams', 'RacunPravnogLica', function($stateParams, RacunPravnogLica) {
                    return RacunPravnogLica.get({id : $stateParams.id});
                }]
            }
        })
        .state('racun-pravnog-lica.new', {
            parent: 'racun-pravnog-lica',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/racun-pravnog-lica/racun-pravnog-lica-dialog.html',
                    controller: 'RacunPravnogLicaDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                brojRacuna: null,
                                datumOtvaranja: null,
                                vazenje: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('racun-pravnog-lica', null, { reload: true });
                }, function() {
//                    $state.go('racun-pravnog-lica');
                });
            }]
        })
        .state('racun-pravnog-lica.edit', {
            parent: 'racun-pravnog-lica',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/racun-pravnog-lica/racun-pravnog-lica-dialog.html',
                    controller: 'RacunPravnogLicaDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['RacunPravnogLica', function(RacunPravnogLica) {
                            return RacunPravnogLica.get({id : $stateParams.id});
                        }]
                    }
                }).result.then(function() {
                    $state.go('racun-pravnog-lica', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('racun-pravnog-lica.delete', {
            parent: 'racun-pravnog-lica',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/racun-pravnog-lica/racun-pravnog-lica-delete-dialog.html',
                    controller: 'RacunPravnogLicaDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['RacunPravnogLica', function(RacunPravnogLica) {
                            return RacunPravnogLica.get({id : $stateParams.id});
                        }]
                    }
                }).result.then(function() {
                    $state.go('racun-pravnog-lica', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('racun-izvod', {
            parent: 'entity',
            url: '/racun-pravnog-lica/{brojRacuna}/izvod/',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'Izvestaj racuna'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/banka/JasperReport1.html',
                }
            },
            resolve: {
            }
        });
    }

})();
