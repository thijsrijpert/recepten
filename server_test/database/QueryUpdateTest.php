<?php
declare(strict_types=1);
use PHPUnit\Framework\TestCase;
require_once(dirname(__FILE__, 3) . '\server\exception\NullPointerException.php');
require_once(dirname(__FILE__, 3) . '\server\database\Query.php');


final class QueryUpdateTest extends TestCase
{

    protected $query;

    public function setUp() : void{
        $this->query = new database\QueryUpdate(new \model\Review());
    }

    public function testSetSetArguments(): void
    {
        $good_input = [['title']];
        $this->assertEquals(
            true,
            $this->query->setSetArguments($good_input)
        );
        $this->assertEquals(
            $good_input,
            $this->query->getSetArguments()
        );
    }

    public function testSetSetArgumentsMultiple(): void
    {
        $good_input = [['title'], ['rating']];
        $this->assertEquals(
            true,
            $this->query->setSetArguments($good_input)
        );
        $this->assertEquals(
            $good_input,
            $this->query->getSetArguments()
        );
    }

    public function testSetSetArgumentsBadArguments(): void
    {
        $good_input = [['illigal_column_name'], ['title']];
        $this->assertEquals(
            false,
            $this->query->setSetArguments($good_input)
        );

        $this->assertEquals(
            array(),
            $this->query->getSetArguments()
        );
    }


}
