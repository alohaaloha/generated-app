'use strict';

describe('Controller Tests', function() {

    describe('Klijent Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockKlijent, MockRacunPravnogLica, MockPravnoLice, MockNaseljenoMesto;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockKlijent = jasmine.createSpy('MockKlijent');
            MockRacunPravnogLica = jasmine.createSpy('MockRacunPravnogLica');
            MockPravnoLice = jasmine.createSpy('MockPravnoLice');
            MockNaseljenoMesto = jasmine.createSpy('MockNaseljenoMesto');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity ,
                'Klijent': MockKlijent,
                'RacunPravnogLica': MockRacunPravnogLica,
                'PravnoLice': MockPravnoLice,
                'NaseljenoMesto': MockNaseljenoMesto
            };
            createController = function() {
                $injector.get('$controller')("KlijentDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'pinfProApp:klijentUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
