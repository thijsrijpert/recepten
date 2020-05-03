<?php
declare(strict_types=1);
use PHPUnit\Framework\TestCase;
require_once(dirname(__FILE__, 3) . '\server\exception\NullPointerException.php');
require_once(dirname(__FILE__, 3) . '\server\database\QueryBuilder.php');
require_once(dirname(__FILE__, 3) . '\server\database\Query.php');
require_once(dirname(__FILE__, 3) . '\server\model\Religion.php');


final class QueryBuilderTest extends TestCase
{
    private $queryBuilder;
    private $mock;

    public function setUp() : void{

        $this->mock = $this->createMock('database\Query');
        $this->mock->selectArguments = [['id'], ['name']];
        $this->mock->whereArguments = [['id', 'eq']];
        $this->mock->orderArguments = [['id', 'DESC']];
        $this->mock->entity = new \model\Religion();

        $this->mock->expects($this->any())->method('getSelectArguments')->will($this->returnValue($this->mock->selectArguments));
        $this->mock->expects($this->any())->method('getWhereArguments')->will($this->returnValue($this->mock->whereArguments));
        $this->mock->expects($this->any())->method('getOrderArguments')->will($this->returnValue($this->mock->orderArguments));
        $this->mock->expects($this->any())->method('getEntity')->will($this->returnValue($this->mock->entity));

        $this->queryBuilder = new \database\QueryBuilder($this->mock);
    }

    public function testGenerateSql(): void
    {
        $output = " SELECT id, name FROM Religion WHERE id = :id ORDER BY id DESC";

        $this->assertEquals(
            true,
            $this->queryBuilder->generateSql()
        );
        $this->assertEquals(
            $output,
            $this->queryBuilder->getSql()
        );
    }
}
