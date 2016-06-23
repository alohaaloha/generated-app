'use strict';

describe('Controller Tests', function() {

    describe('Kliring Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockKliring, MockStavkaKliringa, MockValuta;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockKliring = jasmine.createSpy('MockKliring');
            MockStavkaKliringa = jasmine.createSpy('MockStavkaKliringa');
            MockValuta = jasmine.createSpy('MockValuta');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity ,
                'Kliring': MockKliring,
                'StavkaKliringa': MockStavkaKliringa,
                'Valuta': MockValuta
            };
            createController = function() {
                $injector.get('$controller')("KliringDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'pinfProApp:kliringUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
