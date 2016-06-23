'use strict';

describe('Controller Tests', function() {

    describe('Banka Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockBanka, MockKursnaLista, MockRacunPravnogLica;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockBanka = jasmine.createSpy('MockBanka');
            MockKursnaLista = jasmine.createSpy('MockKursnaLista');
            MockRacunPravnogLica = jasmine.createSpy('MockRacunPravnogLica');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity ,
                'Banka': MockBanka,
                'KursnaLista': MockKursnaLista,
                'RacunPravnogLica': MockRacunPravnogLica
            };
            createController = function() {
                $injector.get('$controller')("BankaDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'pinfProApp:bankaUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
