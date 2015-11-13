
---
## Create a new grid object
---
```java
Grid grid = new Grid();
```

---
## Add some columns and data
---
```java
grid.add("Address", "921 Thompson St");
grid.add("Phone Number", "865-555-9843");
```

---
## Create a new blank row
---
```java
grid.newRow();
```

---
## Add some more columns and data
---
```java
grid.add("Address", "923 Thompson St");
grid.add("Phone Number", "918-555-8340");
```

---
## Write additional data to rows we just wrote
---
```java
grid.add(0, "First Name", "Michael");
grid.add(1, "First Name", "James");
grid.add(0, "Last Name", "Johnson");
grid.add(1, "Last Name", "Smith");
```

---
## Explicitly set row before writing new data
---
```java
grid.setGridRow(0);
grid.add("Misc", "Misc Data");
```

---
## Move between rows
---
```java
grid.setGridRowNext();
grid.add("Misc", "Misc Data");
grid.setGridRowPrev();
grid.add("Misc2", "Misc Data");
grid.setGridRowNext();
grid.add("Misc2", "Misc Data");
```

---
## Print a grid
---
```java
grid.toString();
```
```shell
+-----+-----------------+--------------+------------+-----------+-----------+-----------+
| Row |         Address | Phone Number | First Name | Last Name |      Misc |     Misc2 |
+-----+-----------------+--------------+------------+-----------+-----------+-----------+
|   0 | 921 Thompson St | 865-555-9843 |    Michael |   Johnson | Misc Data | Misc Data |
+-----+-----------------+--------------+------------+-----------+-----------+-----------+
|   1 | 923 Thompson St | 865-555-8340 |      James |     Smith | Misc Data | Misc Data |
+-----+-----------------+--------------+------------+-----------+-----------+-----------+
2 of 2
```
---
## Print a list of lists
---
```java
grid.getListOfLists();
```
```shell
[[Row, Address, Phone Number, First Name, Last Name, Misc, Misc2], [0, 921 Thompson St, 865-555-9843, Michael, Johnson, Misc Data, Misc Data], [1, 923 Thompson St, 865-555-8340, James, Smith, Misc Data, Misc Data]]
```
---
## Print a list of data for supplied column
---
```java
grid.getColumnData("Phone Number");
```
```shell
[865-555-9843, 865-555-8340]
```
---
## Loop through and print out each row
---
```java
grid.resetIteratorNext();
while(grid.hasIteratorNext())
{
    Map<String, Object> map 	= grid.getIteratorCurrentRow();
    int                 index 	= grid.getIteratorPosition();
    if (!map.isEmpty())
    {
	    System.out.print(index + ": ");
	    System.out.println(map);
    }
    grid.getIteratorNext();
}
```
```shell
0: {Address=921 Thompson St, Phone Number=865-555-9843, First Name=Michael, Last Name=Johnson, Misc=Misc Data, Misc2=Misc Data}
1: {Address=923 Thompson St, Phone Number=865-555-8340, First Name=James, Last Name=Smith, Misc=Misc Data, Misc2=Misc Data}

```
---
## Print out rows that match a full string search
---
```java
grid.getIteratorSearchFull("Address", "923 Thompson St")
```
```shell
1: {Address=923 Thompson St, Phone Number=865-555-8340, First Name=James, Last Name=Smith, Misc=Misc Data, Misc2=Misc Data}

```
---
## Print out rows that match a full, case insensitive string search
---
```java
grid.getIteratorSearchFullCaseInsensitive("Address", "923 thompson st");
```
```shell
1: {Address=923 Thompson St, Phone Number=865-555-8340, First Name=James, Last Name=Smith, Misc=Misc Data, Misc2=Misc Data}

```
---
## Print out rows that match a partial string search
---
```java
grid.getIteratorSearchPartial("Address", "Thom");
```
```shell
0: {Address=921 Thompson St, Phone Number=865-555-9843, First Name=Michael, Last Name=Johnson, Misc=Misc Data, Misc2=Misc Data}
1: {Address=923 Thompson St, Phone Number=865-555-8340, First Name=James, Last Name=Smith, Misc=Misc Data, Misc2=Misc Data}

```
---
## Print out rows that match a partial, case insensitive string search
---
```java
grid.getIteratorSearchPartialCaseInsensitive("Address", "thom");
```
```shell
0: {Address=921 Thompson St, Phone Number=865-555-9843, First Name=Michael, Last Name=Johnson, Misc=Misc Data, Misc2=Misc Data}
1: {Address=923 Thompson St, Phone Number=865-555-8340, First Name=James, Last Name=Smith, Misc=Misc Data, Misc2=Misc Data}

```
---
## Return a list of rows matching search
---
```java
List<Map<String, Object>> search1 = grid.getListSearchPartialCaseInsensitive("Address", "thom");
search1.toString();
```
```shell
[{Address=921 Thompson St, Phone Number=865-555-9843, First Name=Michael, Last Name=Johnson, Misc=Misc Data, Misc2=Misc Data}, {Address=923 Thompson St, Phone Number=865-555-8340, First Name=James, Last Name=Smith, Misc=Misc Data, Misc2=Misc Data}]
```
---
## Return a new grid object of rows matching search
---
```java
List<Map<String, Object>> search2 = grid.getObjectSearchPartialCaseInsensitive("Address", "thom");
System.out.println(search2.toString());
```
```shell
+-----+-----------------+--------------+------------+-----------+-----------+-----------+
| Row |         Address | Phone Number | First Name | Last Name |      Misc |     Misc2 |
+-----+-----------------+--------------+------------+-----------+-----------+-----------+
|   0 | 921 Thompson St | 865-555-9843 |    Michael |   Johnson | Misc Data | Misc Data |
+-----+-----------------+--------------+------------+-----------+-----------+-----------+
|   1 | 923 Thompson St | 865-555-8340 |      James |     Smith | Misc Data | Misc Data |
+-----+-----------------+--------------+------------+-----------+-----------+-----------+
2 of 2
```
---
## Return total amount of rows
---
```java
grid.getTotalRowCount();
```
```shell
2
```
---
## Return a map for a row number
---
```java
grid.getRowByNumber(0);
```
```shell
{Address=921 Thompson St, Phone Number=865-555-9843, First Name=Michael, Last Name=Johnson, Misc=Misc Data, Misc2=Misc Data}
```
---
## Return a value for a row number and column
---
```java
grid.getValueByRowAndColumn(0, "Phone Number");
```
```shell
865-555-9843
```
---
## Return grid column names
---
```java
grid.getColumns();
```
```shell
[Address, Phone Number, First Name, Last Name, Misc, Misc2]
```
---
## Remove some columns and verify
---
```java
grid.removeColumn("Misc");
grid.removeColumn("Misc2");
grid.getColumns()

```
```shell
[Address, Phone Number, First Name, Last Name]
```
---
## Print a grid
---
```java
grid.toString();
```
```shell
+-----+-----------------+--------------+------------+-----------+
| Row |         Address | Phone Number | First Name | Last Name |
+-----+-----------------+--------------+------------+-----------+
|   0 | 921 Thompson St | 865-555-9843 |    Michael |   Johnson |
+-----+-----------------+--------------+------------+-----------+
|   1 | 923 Thompson St | 865-555-8340 |      James |     Smith |
+-----+-----------------+--------------+------------+-----------+
2 of 2
```