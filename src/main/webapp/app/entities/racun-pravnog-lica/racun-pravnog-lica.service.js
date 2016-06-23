(function() {
    'use strict';
    angular
        .module('pinfProApp')
        .factory('RacunPravnogLica', RacunPravnogLica);

    RacunPravnogLica.$inject = ['$resource', 'DateUtils'];

    function RacunPravnogLica ($resource, DateUtils) {
        var resourceUrl =  'api/racun-pravnog-licas/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    data = angular.fromJson(data);
                    data.datumOtvaranja = DateUtils.convertDateTimeFromServer(data.datumOtvaranja);
                    return data;
                }
            },
            'update': { method:'PUT' }
        });
    }
})();
