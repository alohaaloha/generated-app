'use strict';

describe('Controller Tests', function() {

    describe('RacunPravnogLica Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockRacunPravnogLica, MockBanka, MockKlijent, MockDnevnoStanjeRacuna, MockValuta, MockUkidanje;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockRacunPravnogLica = jasmine.createSpy('MockRacunPravnogLica');
            MockBanka = jasmine.createSpy('MockBanka');
            MockKlijent = jasmine.createSpy('MockKlijent');
            MockDnevnoStanjeRacuna = jasmine.createSpy('MockDnevnoStanjeRacuna');
            MockValuta = jasmine.createSpy('MockValuta');
            MockUkidanje = jasmine.createSpy('MockUkidanje');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity ,
                'RacunPravnogLica': MockRacunPravnogLica,
                'Banka': MockBanka,
                'Klijent': MockKlijent,
                'DnevnoStanjeRacuna': MockDnevnoStanjeRacuna,
                'Valuta': MockValuta,
                'Ukidanje': MockUkidanje
            };
            createController = function() {
                $injector.get('$controller')("RacunPravnogLicaDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'pinfProApp:racunPravnogLicaUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
