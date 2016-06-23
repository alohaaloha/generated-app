'use strict';

describe('Controller Tests', function() {

    describe('VrstaPlacanja Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockVrstaPlacanja, MockAnalitikaIzvoda;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockVrstaPlacanja = jasmine.createSpy('MockVrstaPlacanja');
            MockAnalitikaIzvoda = jasmine.createSpy('MockAnalitikaIzvoda');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity ,
                'VrstaPlacanja': MockVrstaPlacanja,
                'AnalitikaIzvoda': MockAnalitikaIzvoda
            };
            createController = function() {
                $injector.get('$controller')("VrstaPlacanjaDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'pinfProApp:vrstaPlacanjaUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
