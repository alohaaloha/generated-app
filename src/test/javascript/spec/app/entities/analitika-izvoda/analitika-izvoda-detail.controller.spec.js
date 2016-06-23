'use strict';

describe('Controller Tests', function() {

    describe('AnalitikaIzvoda Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockAnalitikaIzvoda, MockDnevnoStanjeRacuna, MockNaseljenoMesto, MockVrstaPlacanja, MockValuta, MockRTGS, MockStavkaKliringa;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockAnalitikaIzvoda = jasmine.createSpy('MockAnalitikaIzvoda');
            MockDnevnoStanjeRacuna = jasmine.createSpy('MockDnevnoStanjeRacuna');
            MockNaseljenoMesto = jasmine.createSpy('MockNaseljenoMesto');
            MockVrstaPlacanja = jasmine.createSpy('MockVrstaPlacanja');
            MockValuta = jasmine.createSpy('MockValuta');
            MockRTGS = jasmine.createSpy('MockRTGS');
            MockStavkaKliringa = jasmine.createSpy('MockStavkaKliringa');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity ,
                'AnalitikaIzvoda': MockAnalitikaIzvoda,
                'DnevnoStanjeRacuna': MockDnevnoStanjeRacuna,
                'NaseljenoMesto': MockNaseljenoMesto,
                'VrstaPlacanja': MockVrstaPlacanja,
                'Valuta': MockValuta,
                'RTGS': MockRTGS,
                'StavkaKliringa': MockStavkaKliringa
            };
            createController = function() {
                $injector.get('$controller')("AnalitikaIzvodaDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'pinfProApp:analitikaIzvodaUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
