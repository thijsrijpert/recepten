<?php
namespace database;
require_once(dirname(__FILE__,1) . '/CRInterface.php');
interface CRUInterface extends CRInterface{
    function update(\model\Model $model, \model\Model $modelOld) : String;
}
