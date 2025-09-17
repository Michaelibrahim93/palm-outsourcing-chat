# palm-outsourcing-chat

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