# SpaceLayoutDecoration
RecyclerView decoration

This is an ItemDecoration class implemented RecyclerView.ItemDecoration. It can help us with item space issue when we have to deal with head space , tail space and grid space.


Here's how we use it. If your list is grid type, you can do it with this:

```

    mRecyclerView.addItemDecoration(
        new SpaceLayoutDecoration.Builder(SpaceLayoutDecoration.GRID_LAYOUT).span(4)
            .rowSpace(12)
            .columnSpace(12)
            .headCut(0)
            .tailCut(0).leftCut(12).rightCut(12).build());
```

or this:

:
```
mRecyclerView.addItemDecoration(
        new SpaceLayoutDecoration.Builder(SpaceLayoutDecoration.GRID_LAYOUT).span(2)
            .headCut(0)
            .tailCut(0)
            .rowSpace(context.getResources().getDimensionPixelOffset(R.dimen.activity_horizontal_margin))
            .columnSpace(context.getResources().getDimensionPixelOffset(R.dimen.activity_horizontal_margin))
            .build());
```

If your list is linear type, you can do it with this:

```
listView.addItemDecoration(
        new SpaceLayoutDecoration.Builder(SpaceLayoutDecoration.LINEAR_LAYOUT_VERTICAL).linearSpace(
            50).headCut(0).tailCut(0).build());
```

