'use strict';

angular.module('clientAppApp')
.directive('exportTable', [function(){
	// export html table to pdf, excel and doc format directive
	// var exportTable = function(){
		// var link = function($scope, elm, attr, scope){
		// 	if (scope.selector) {
		// 		$scope.$on('export-pdf', function(e, d){
		// 			elm.tableExport({type:'pdf', escape:false});
		// 		});
		// 		$scope.$on('export-excel', function(e, d){
		// 			elm.tableExport({type:'excel', escape:'false'});
		// 		});
		// 		$scope.$on('export-doc', function(e, d){
		// 			elm.tableExport({type: 'doc', escape:false});
		// 		});
		// 	};
		// }

		return {
			restrict: 'AC',
			// scope: {
			// 	selector: '=exportTable'
			// },
			link: function($scope, elm, attr, controller, transcludeFn){
				$scope.$on('export-pdf', function(e, d){
					if (attr.exportTable == 'true') {
						elm.tableExport({type:'pdf', escape:false});
					};
				});
				$scope.$on('export-excel', function(e, d){
					if (attr.exportTable == 'true') {
						elm.tableExport({type:'excel', escape:'false'});
					};
				});
				$scope.$on('export-doc', function(e, d){
					if (attr.exportTable == 'true') {
						elm.tableExport({type: 'doc', escape:'false'});
					};
				});
				
			}
		};
	// }
}]);
