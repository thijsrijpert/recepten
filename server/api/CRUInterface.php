<?php
namespace api;
require_once(dirname(__FILE__,1) . '/CRInterface.php');
interface CRUInterface extends CRInterface{
    function update();
}
