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
        $this->queryBuilder = new \database\QueryBuilder($this->mock);
    }

    public function testGenerateSql(): void
    {
        $output = "SELECT id FROM Religion WHERE id = :id ORDER BY id DESC";

        $this->mock->selectArguments = [['id']];
        $this->mock->whereArguments = [['id', '=']];
        $this->mock->orderArguments = [['id', 'DESC']];

        $this->mock->entity = new \model\Religion();

        $this->mock->expects($this->any())->method('getSelectArguments')->will($this->returnValue($this->mock->selectArguments));
        $this->mock->expects($this->any())->method('getWhereArguments')->will($this->returnValue($this->mock->whereArguments));
        $this->mock->expects($this->any())->method('getOrderArguments')->will($this->returnValue($this->mock->orderArguments));
        $this->mock->expects($this->any())->method('getEntity')->will($this->returnValue($this->mock->entity));

        $this->queryBuilder->setQuery($this->mock);

        $this->assertEquals(
            true,
            $this->queryBuilder->generateSql()
        );
        $this->assertEquals(
            $output,
            $this->queryBuilder->getSql()
        );
    }

    public function testGenerateSqlSelectAll(): void
    {
        $output = "SELECT * FROM Religion WHERE id = :id ORDER BY id DESC";

        $this->mock->selectArguments = [['*']];
        $this->mock->whereArguments = [['id', '=']];
        $this->mock->orderArguments = [['id', 'DESC']];
        $this->mock->entity = new \model\Religion();

        $this->mock->expects($this->any())->method('getSelectArguments')->will($this->returnValue($this->mock->selectArguments));
        $this->mock->expects($this->any())->method('getWhereArguments')->will($this->returnValue($this->mock->whereArguments));
        $this->mock->expects($this->any())->method('getOrderArguments')->will($this->returnValue($this->mock->orderArguments));
        $this->mock->expects($this->any())->method('getEntity')->will($this->returnValue($this->mock->entity));

        $this->queryBuilder->setQuery($this->mock);

        $this->assertEquals(
            true,
            $this->queryBuilder->generateSql()
        );
        $this->assertEquals(
            $output,
            $this->queryBuilder->getSql()
        );
    }

    public function testGenerateSqlDouble(): void
    {
        $output = "SELECT id, name FROM Religion WHERE name = :name AND id > :id ORDER BY id DESC, name ASC";

        $this->mock->selectArguments = [['id'], ['name']];
        $this->mock->whereArguments = [['name', '='], ['id', '>']];
        $this->mock->orderArguments = [['id', 'DESC'], ['name', 'ASC']];
        $this->mock->entity = new \model\Religion();

        $this->mock->expects($this->any())->method('getSelectArguments')->will($this->returnValue($this->mock->selectArguments));
        $this->mock->expects($this->any())->method('getWhereArguments')->will($this->returnValue($this->mock->whereArguments));
        $this->mock->expects($this->any())->method('getOrderArguments')->will($this->returnValue($this->mock->orderArguments));
        $this->mock->expects($this->any())->method('getEntity')->will($this->returnValue($this->mock->entity));

        $this->queryBuilder->setQuery($this->mock);

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
