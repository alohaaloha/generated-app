(function() {
    'use strict';
    angular
        .module('pinfProApp')
        .factory('AnalitikaIzvoda', AnalitikaIzvoda);

    AnalitikaIzvoda.$inject = ['$resource', 'DateUtils'];

    function AnalitikaIzvoda ($resource, DateUtils) {
        var resourceUrl =  'api/analitika-izvodas/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    data = angular.fromJson(data);
                    data.datumPrijema = DateUtils.convertDateTimeFromServer(data.datumPrijema);
                    data.datumValute = DateUtils.convertDateTimeFromServer(data.datumValute);
                    return data;
                }
            },
            'update': { method:'PUT' }
        });
    }
})();
