'use strict';

describe('Controller Tests', function() {

    describe('Ukidanje Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockUkidanje, MockRacunPravnogLica;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockUkidanje = jasmine.createSpy('MockUkidanje');
            MockRacunPravnogLica = jasmine.createSpy('MockRacunPravnogLica');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity ,
                'Ukidanje': MockUkidanje,
                'RacunPravnogLica': MockRacunPravnogLica
            };
            createController = function() {
                $injector.get('$controller')("UkidanjeDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'pinfProApp:ukidanjeUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
