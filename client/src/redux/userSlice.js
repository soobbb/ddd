import { createSlice } from '@reduxjs/toolkit';

export const userSlice = createSlice({
  name: 'user',
  initialState: { value: { email: '' } },
  reducers: {
    login: (state, action) => {
      state.value = action.payload;
    },
  },
});

export default userSlice.reducer;
export const { login } = userSlice.actions;
