'use strict';

describe('Controller Tests', function() {

    describe('NaseljenoMesto Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockNaseljenoMesto, MockDrzava, MockAnalitikaIzvoda;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockNaseljenoMesto = jasmine.createSpy('MockNaseljenoMesto');
            MockDrzava = jasmine.createSpy('MockDrzava');
            MockAnalitikaIzvoda = jasmine.createSpy('MockAnalitikaIzvoda');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity ,
                'NaseljenoMesto': MockNaseljenoMesto,
                'Drzava': MockDrzava,
                'AnalitikaIzvoda': MockAnalitikaIzvoda
            };
            createController = function() {
                $injector.get('$controller')("NaseljenoMestoDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'pinfProApp:naseljenoMestoUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
