'use strict';

describe('Controller: DesempCtrl', function () {

  // load the controller's module
  beforeEach(module('clientAppApp'));

  var DesempCtrl,
    scope;

  // Initialize the controller and a mock scope
  beforeEach(inject(function ($controller, $rootScope) {
    scope = $rootScope.$new();
    DesempCtrl = $controller('DesempCtrl', {
      $scope: scope
      // place here mocked dependencies
    });
  }));

  it('should attach a list of awesomeThings to the scope', function () {
    expect(DesempCtrl.awesomeThings.length).toBe(3);
  });
});
