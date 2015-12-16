'use strict';

describe('Controller: AnioCtrl', function () {

  // load the controller's module
  beforeEach(module('clientAppApp'));

  var AnioCtrl,
    scope;

  // Initialize the controller and a mock scope
  beforeEach(inject(function ($controller, $rootScope) {
    scope = $rootScope.$new();
    AnioCtrl = $controller('AnioCtrl', {
      $scope: scope
      // place here mocked dependencies
    });
  }));

  it('should attach a list of awesomeThings to the scope', function () {
    expect(AnioCtrl.awesomeThings.length).toBe(3);
  });
});
