(function() {
    'use strict';

    angular
        .module('pinfProApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('klijent', {
            parent: 'entity',
            url: '/klijent',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'Klijents'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/klijent/klijents.html',
                    controller: 'KlijentController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
            }
        })
        .state('klijent-detail', {
            parent: 'entity',
            url: '/klijent/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'Klijent'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/klijent/klijent-detail.html',
                    controller: 'KlijentDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                entity: ['$stateParams', 'Klijent', function($stateParams, Klijent) {
                    return Klijent.get({id : $stateParams.id});
                }]
            }
        })
        .state('klijent.new', {
            parent: 'klijent',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/klijent/klijent-dialog.html',
                    controller: 'KlijentDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                nazivPravnogLica: null,
                                ime: null,
                                prezime: null,
                                jmbg: null,
                                adresa: null,
                                telefon: null,
                                fax: null,
                                email: null,
                                pib: null,
                                sifraDelatnosti: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('klijent', null, { reload: true });
                }, function() {
                    $state.go('klijent');
                });
            }]
        })
        .state('klijent.edit', {
            parent: 'klijent',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/klijent/klijent-dialog.html',
                    controller: 'KlijentDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Klijent', function(Klijent) {
                            return Klijent.get({id : $stateParams.id});
                        }]
                    }
                }).result.then(function() {
                    $state.go('klijent', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('klijent.delete', {
            parent: 'klijent',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/klijent/klijent-delete-dialog.html',
                    controller: 'KlijentDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Klijent', function(Klijent) {
                            return Klijent.get({id : $stateParams.id});
                        }]
                    }
                }).result.then(function() {
                    $state.go('klijent', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
