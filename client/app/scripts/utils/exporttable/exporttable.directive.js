'use strict';

angular.module('clientAppApp')
.directive('exportTable', [function(){

	return {

		restrict: 'AC',

		link: function($scope, elm, attr, controller, transcludeFn){


			function selector(attr, id, fileType){

				if (attr.exportTable == id) {
					elm.tableExport({type:fileType, escape:false});
				};
			};

			$scope.$on('export-pdf', function(e, d){
				selector(attr, d.id, 'pdf');
			});
			$scope.$on('export-excel', function(e, d){
				selector(attr, d.id, 'excel');
			});
			$scope.$on('export-doc', function(e, d){
				selector(attr, d.id, 'doc');
			});
			
		}
	};
}]);
