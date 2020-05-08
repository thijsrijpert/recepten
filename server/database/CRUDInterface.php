<?php
namespace database;
    interface CRUDInterface extends CRUInterface{
        abstract function delete(\model\Model $model) : String;
    }
