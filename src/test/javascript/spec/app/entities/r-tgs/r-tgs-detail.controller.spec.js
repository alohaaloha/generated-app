'use strict';

describe('Controller Tests', function() {

    describe('RTGS Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockRTGS, MockAnalitikaIzvoda;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockRTGS = jasmine.createSpy('MockRTGS');
            MockAnalitikaIzvoda = jasmine.createSpy('MockAnalitikaIzvoda');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity ,
                'RTGS': MockRTGS,
                'AnalitikaIzvoda': MockAnalitikaIzvoda
            };
            createController = function() {
                $injector.get('$controller')("RTGSDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'pinfProApp:rTGSUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
