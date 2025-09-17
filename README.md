# Palm Outsourcing Task


## Lifecycle Fix and Migration Decision

### Fix Applied
I replaced the LiveData observer from using the **Activity lifecycle** to the **Fragment view lifecycle**.  
This prevents crashes and memory leaks since the fragment’s view can be destroyed while the Activity is still alive.  
LiveData should always be bound to the view’s lifecycle to avoid dangling observers.

```kotlin
// Old (causing crashes and memory leaks)
viewModel.messages.observe(requireActivity())

// New (safe and lifecycle-aware)
viewModel.messages.observe(viewLifecycleOwner)
```

### Why Not Migrating to StateFlow Yet
We’re not migrating this legacy code to Flow/StateFlow right now because:
- Migration would be time-consuming, requiring refactoring across multiple layers.
- The priority is to deliver a hotfix for the crash and memory leak.
- Using viewLifecycleOwner provides a safe and reliable fix in the short term.

## Code Review
### Observing LiveData
```kotlin
// Current (unsafe, tied to Activity lifecycle)
viewModel.messages.observe(requireActivity()) { msgs ->
    recyclerView.adapter = MessagesAdapter(msgs)
}

// Recommended (safe, tied to Fragment's view lifecycle)
viewModel.messages.observe(viewLifecycleOwner) { msgs ->
    //better performance to update only the changed items
    messagesAdapter.submitList(msgs)
}
```
### Why viewLifecycleOwner?
Using **requireActivity()** ties the observer to the Activity lifecycle.
In Fragments, the view can be destroyed or detached while the Activity is still alive.
This mismatch can cause memory leaks and crashes.
viewLifecycleOwner safely binds LiveData to the Fragment’s view lifecycle.

### RecyclerView Adapter
**Issue in current code:**
- A new MessagesAdapter is being created inside the LiveData observer.
- This means every update replaces the adapter entirely, forcing the RecyclerView to redraw the entire list.
- This causes performance issues (especially with large or frequently updating lists).
**Recommended solution:**
- Initialize the adapter once outside the observer.
- Use `ListAdapter` with `DiffUtil` and `submitList()` inside the observer.
- This ensures only the changed items are updated, improving performance and keeping code cleaner.
- Search about `ListAdapter` vs `RecyclerView.Adapter` for more details.

## Unit tests
Please note:  
If you want to run **all of your test cases**, use the following command in the terminal:
```bash
./gradlew check connectedAndroidTest
```
### Test Results Screenshot
![Chat screen](Screenshots/UnitTestsScreenShot.png)
