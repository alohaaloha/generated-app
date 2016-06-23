'use strict';

describe('Controller Tests', function() {

    describe('StavkaKliringa Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockStavkaKliringa, MockKliring, MockAnalitikaIzvoda;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockStavkaKliringa = jasmine.createSpy('MockStavkaKliringa');
            MockKliring = jasmine.createSpy('MockKliring');
            MockAnalitikaIzvoda = jasmine.createSpy('MockAnalitikaIzvoda');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity ,
                'StavkaKliringa': MockStavkaKliringa,
                'Kliring': MockKliring,
                'AnalitikaIzvoda': MockAnalitikaIzvoda
            };
            createController = function() {
                $injector.get('$controller')("StavkaKliringaDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'pinfProApp:stavkaKliringaUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
