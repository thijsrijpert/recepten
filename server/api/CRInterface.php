<?php
namespace api;
interface CRInterface{
    abstract function select() : void;

    abstract function insert() : void;
}
