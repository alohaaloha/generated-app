'use strict';

describe('Controller Tests', function() {

    describe('DnevnoStanjeRacuna Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockDnevnoStanjeRacuna, MockAnalitikaIzvoda, MockRacunPravnogLica;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockDnevnoStanjeRacuna = jasmine.createSpy('MockDnevnoStanjeRacuna');
            MockAnalitikaIzvoda = jasmine.createSpy('MockAnalitikaIzvoda');
            MockRacunPravnogLica = jasmine.createSpy('MockRacunPravnogLica');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity ,
                'DnevnoStanjeRacuna': MockDnevnoStanjeRacuna,
                'AnalitikaIzvoda': MockAnalitikaIzvoda,
                'RacunPravnogLica': MockRacunPravnogLica
            };
            createController = function() {
                $injector.get('$controller')("DnevnoStanjeRacunaDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'pinfProApp:dnevnoStanjeRacunaUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
