'use strict';

describe('Controller Tests', function() {

    describe('Valuta Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockValuta, MockDrzava, MockKliring, MockKursUValuti, MockRacunPravnogLica, MockAnalitikaIzvoda;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockValuta = jasmine.createSpy('MockValuta');
            MockDrzava = jasmine.createSpy('MockDrzava');
            MockKliring = jasmine.createSpy('MockKliring');
            MockKursUValuti = jasmine.createSpy('MockKursUValuti');
            MockRacunPravnogLica = jasmine.createSpy('MockRacunPravnogLica');
            MockAnalitikaIzvoda = jasmine.createSpy('MockAnalitikaIzvoda');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity ,
                'Valuta': MockValuta,
                'Drzava': MockDrzava,
                'Kliring': MockKliring,
                'KursUValuti': MockKursUValuti,
                'RacunPravnogLica': MockRacunPravnogLica,
                'AnalitikaIzvoda': MockAnalitikaIzvoda
            };
            createController = function() {
                $injector.get('$controller')("ValutaDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'pinfProApp:valutaUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
