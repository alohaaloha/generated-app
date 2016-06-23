'use strict';

describe('Controller Tests', function() {

    describe('Drzava Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockDrzava, MockNaseljenoMesto, MockValuta;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockDrzava = jasmine.createSpy('MockDrzava');
            MockNaseljenoMesto = jasmine.createSpy('MockNaseljenoMesto');
            MockValuta = jasmine.createSpy('MockValuta');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity ,
                'Drzava': MockDrzava,
                'NaseljenoMesto': MockNaseljenoMesto,
                'Valuta': MockValuta
            };
            createController = function() {
                $injector.get('$controller')("DrzavaDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'pinfProApp:drzavaUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
