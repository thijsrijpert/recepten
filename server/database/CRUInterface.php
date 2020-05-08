<?php
namespace database;
interface CRUInterface extends CRInterface{
    abstract function update(\model\Model $model) : String;
}
