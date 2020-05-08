<?php
namespace api;
interface CRUDInterface extends CRUInterface{
    abstract function delete() : void;
}
