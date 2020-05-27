<?php
namespace api;
require_once(dirname(__FILE__,1) . '/CRUInterface.php');
interface CRUDInterface extends CRUInterface{
    function delete();
}
