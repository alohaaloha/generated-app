(function() {
    'use strict';

    angular
        .module('pinfProApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('kurs-u-valuti', {
            parent: 'entity',
            url: '/kurs-u-valuti',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'KursUValutis'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/kurs-u-valuti/kurs-u-valutis.html',
                    controller: 'KursUValutiController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
            }
        })
        .state('kurs-u-valuti-detail', {
            parent: 'entity',
            url: '/kurs-u-valuti/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'KursUValuti'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/kurs-u-valuti/kurs-u-valuti-detail.html',
                    controller: 'KursUValutiDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                entity: ['$stateParams', 'KursUValuti', function($stateParams, KursUValuti) {
                    return KursUValuti.get({id : $stateParams.id});
                }]
            }
        })
        .state('kurs-u-valuti.new', {
            parent: 'kurs-u-valuti',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/kurs-u-valuti/kurs-u-valuti-dialog.html',
                    controller: 'KursUValutiDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                redniBroj: null,
                                kursKupovni: null,
                                kursSrednji: null,
                                kursProdajni: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('kurs-u-valuti', null, { reload: true });
                }, function() {
                    $state.go('kurs-u-valuti');
                });
            }]
        })
        .state('kurs-u-valuti.edit', {
            parent: 'kurs-u-valuti',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/kurs-u-valuti/kurs-u-valuti-dialog.html',
                    controller: 'KursUValutiDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['KursUValuti', function(KursUValuti) {
                            return KursUValuti.get({id : $stateParams.id});
                        }]
                    }
                }).result.then(function() {
                    $state.go('kurs-u-valuti', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('kurs-u-valuti.delete', {
            parent: 'kurs-u-valuti',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/kurs-u-valuti/kurs-u-valuti-delete-dialog.html',
                    controller: 'KursUValutiDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['KursUValuti', function(KursUValuti) {
                            return KursUValuti.get({id : $stateParams.id});
                        }]
                    }
                }).result.then(function() {
                    $state.go('kurs-u-valuti', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
