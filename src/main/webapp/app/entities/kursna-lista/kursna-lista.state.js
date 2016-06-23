(function() {
    'use strict';

    angular
        .module('pinfProApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('kursna-lista', {
            parent: 'entity',
            url: '/kursna-lista',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'KursnaListas'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/kursna-lista/kursna-listas.html',
                    controller: 'KursnaListaController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
            }
        })
        .state('kursna-lista-detail', {
            parent: 'entity',
            url: '/kursna-lista/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'KursnaLista'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/kursna-lista/kursna-lista-detail.html',
                    controller: 'KursnaListaDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                entity: ['$stateParams', 'KursnaLista', function($stateParams, KursnaLista) {
                    return KursnaLista.get({id : $stateParams.id});
                }]
            }
        })
        .state('kursna-lista.new', {
            parent: 'kursna-lista',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/kursna-lista/kursna-lista-dialog.html',
                    controller: 'KursnaListaDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                datum: null,
                                brojKursneListe: null,
                                datumPrimene: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('kursna-lista', null, { reload: true });
                }, function() {
                    $state.go('kursna-lista');
                });
            }]
        })
        .state('kursna-lista.edit', {
            parent: 'kursna-lista',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/kursna-lista/kursna-lista-dialog.html',
                    controller: 'KursnaListaDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['KursnaLista', function(KursnaLista) {
                            return KursnaLista.get({id : $stateParams.id});
                        }]
                    }
                }).result.then(function() {
                    $state.go('kursna-lista', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('kursna-lista.delete', {
            parent: 'kursna-lista',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/kursna-lista/kursna-lista-delete-dialog.html',
                    controller: 'KursnaListaDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['KursnaLista', function(KursnaLista) {
                            return KursnaLista.get({id : $stateParams.id});
                        }]
                    }
                }).result.then(function() {
                    $state.go('kursna-lista', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
